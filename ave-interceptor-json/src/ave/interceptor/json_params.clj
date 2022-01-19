(ns ave.interceptor.json-params
  (:require
   [integrant.core :as ig]
   [ring.middleware.json :as json]

   [clojure.spec.alpha :as s]))


(defn make [& options]
  {:name ::interceptor
   :enter
   (fn [{:as ctx :keys [request]}]

     (if-let [request*
              (json/json-params-request request options)]

       (assoc ctx :request request*)

       (let [message
             "Malformed JSON in request body"]

         (throw (ex-info message
                         {:ex/type ::malformed-json
                          :http/status 400
                          :http/content-type "text/plain"
                          :http/message message})))))})


(def default
  (make))


(defmethod ig/init-key ::ig
  [_ options]
  (make options))


(defmethod ig/pre-init-spec ::ig [_]
  ::config)


(s/def ::config
  (s/keys :opt-un [::key-fn
                   ::keywords?]))

(s/def ::key-fn ifn?)
(s/def ::keywords? boolean?)
