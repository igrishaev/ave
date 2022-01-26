(ns ave.interceptor.json-body
  (:require
   [integrant.core :as ig]
   [exoscale.interceptor :as interceptor]
   [ring.middleware.json :as json]

   [clojure.spec.alpha :as s]))


(defmethod ig/init-key ::*
  [_ {:as options
      :keys [malformed-response]}]

  {:name ::interceptor
   :enter
   (fn [{:as ctx :keys [request]}]

     (if-let [request*
              (json/json-body-request request options)]

       (assoc ctx :request request*)

       (let [response
             (or malformed-response
                 json/default-malformed-response)]

         (interceptor/terminate (assoc ctx :response response)))))})


(defmethod ig/pre-init-spec ::* [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::key-fn
                   ::keywords?
                   ::bigdecimals?
                   ::malformed-response]))

(s/def ::key-fn ifn?)
(s/def ::keywords? boolean?)
(s/def ::bigdecimals? boolean?)
(s/def ::malformed-response map?)
