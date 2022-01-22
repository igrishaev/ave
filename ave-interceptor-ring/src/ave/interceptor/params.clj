(ns ave.interceptor.params
  (:require
   [ring.middleware.params :as params]
   [integrant.core :as ig]

   [clojure.spec.alpha :as s]))


(defn make [& [options]]
  {:name ::interceptor
   :enter
   (fn [ctx]
     (update ctx :request params/params-request options))})


(def default
  (make))


(defmethod ig/init-key ::ig
  [_ options]
  (make options))


(defmethod ig/pre-init-spec ::ig [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::encoding]))


(s/def ::encoding string?)
