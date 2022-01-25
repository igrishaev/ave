(ns ave.router-reitit
  (:require
   [reitit.core :as r]
   [integrant.core :as ig]

   [exoscale.interceptor :as interceptor]
   [clojure.spec.alpha :as s]))


(def interceptor-response
  {:name ::response
   :leave :response})


(defn handler->interceptor
  [handler]
  {:name ::handler
   :enter
   (fn [ctx]
     (assoc ctx :response (handler ctx)))})


(def defaults
  {:conflicts nil})


(defmethod ig/init-key ::*
  [_ {:keys [routes
             options
             interceptors]}]

  (let [router
        (r/router routes (merge defaults options))]

    (fn [request]

      (let [{:keys [uri
                    request-method]}
            request

            {:keys [data
                    path
                    path-params]}
            (r/match-by-path router uri)

            handler
            (or (get data :any)
                (get data request-method)
                (or (throw (ex-info "Route not found"
                                    {:uri uri
                                     :method request-method
                                     :options options}))))

            path-params
            (dissoc path-params (keyword ""))

            {interceptors-route :interceptors}
            data

            request*
            (assoc request
                   :path path
                   :path-params path-params)

            stack
            (-> []
                (conj interceptor-response)
                (into interceptors)
                (into interceptors-route)
                (conj (handler->interceptor handler)))]

        (interceptor/execute {:request request*} stack)))))


(defmethod ig/pre-init-spec ::* [_]
  ::config)


(s/def ::config
  (s/keys :req-un [::routes]
          :opt-un [::options
                   ::interceptors]))

(s/def ::routes vector?)
(s/def ::options map?)


(s/def ::interceptors
  (s/coll-of ::interceptor))


(s/def ::interceptor map?)
