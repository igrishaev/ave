(ns ave.interceptor.json-body
  (:require
   [integrant.core :as ig]
   [ring.middleware.json :as json]

   [clojure.spec.alpha :as s]))


(defmethod ig/init-key ::*
  [_ options]

  {:name ::interceptor
   :enter
   (fn [{:as ctx :keys [request]}]

     (if-let [request*
              (json/json-body-request request options)]

       (assoc ctx :request request*)

       (let [message
             "Malformed JSON in request body"]

         (throw (ex-info message
                         {:ex/type ::malformed-json
                          :http/status 400
                          :http/content-type "text/plain"
                          :http/message message})))))})


(defmethod ig/pre-init-spec ::* [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::key-fn
                   ::keywords?
                   ::bigdecimals?]))

(s/def ::key-fn ifn?)
(s/def ::keywords? boolean?)
(s/def ::bigdecimals? boolean?)
