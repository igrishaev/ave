(ns ave.interceptor.json.response
  (:require
   [ave.interceptor.json.response.spec :as spec]

   [integrant.core :as ig]
   [ring.middleware.json :as json]))


(defn make [& options]
  {:name ::interceptor
   :leave
   (fn [ctx]
     (update ctx :response json/json-response options))})


(def default
  (make))


(defmethod ig/init-key ::ig
  [_ options]
  (make options))


(defmethod ig/pre-init-spec ::ig [_]
  ::spec/config)
