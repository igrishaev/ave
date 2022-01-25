(ns ave.interceptor.params
  (:require
   [integrant.core :as ig]
   [ring.middleware.params :as params]

   [clojure.spec.alpha :as s]))


(defmethod ig/init-key ::*
  [_ options]
  {:name ::interceptor
   :enter
   (fn [ctx]
     (update ctx :request params/params-request options))})


(defmethod ig/pre-init-spec ::* [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::encoding]))


(s/def ::encoding string?)
