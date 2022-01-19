(ns ave.interceptor.json
  (:require
   [ring.middleware.json :as json]))


(defn make-json-response [& [options]]
  {:name ::json-response
   :leave
   (fn [ctx]
     (update ctx :response json/json-response options))})


(def json-response-default
  (make-json-response))


(defn make-json-body [& [options]]
  {:name ::json-body
   :enter
   (fn [{:as ctx :keys [request]}]

     (if-let [request*
              (json/json-body-request request options)]

       (assoc ctx :request request*)

       (let [message
             "Malformed JSON in request body"]

         (throw (ex-info message
                         {:ex/type ::malformed-json
                          :http/code 400
                          :http/content-type "text/plain"
                          :http/message message})))))})


(def json-body-default
  (make-json-body))


(defn make-json-params [& [options]]
  {:name ::json-params
   :enter
   (fn [{:as ctx :keys [request]}]

     (if-let [request*
              (json/json-params-request request options)]

       (assoc ctx :request request*)

       (let [message
             "Malformed JSON in request body."]

         (throw (ex-info message
                         {:ex/type ::malformed-json
                          :http/code 400
                          :http/message message
                          :header/content-type "text/plain"})))))})


(def json-params-default
  (make-json-params))
