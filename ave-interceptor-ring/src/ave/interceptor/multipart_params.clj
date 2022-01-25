(ns ave.interceptor.multipart-params
  (:require
   [integrant.core :as ig]
   [ring.middleware.multipart-params :as mp]

   [clojure.spec.alpha :as s]))


(defmethod ig/init-key ::*
  [_ options]
  {:name ::interceptor
   :enter
   (fn [ctx]
     (update ctx :request mp/multipart-params-request options))})


(defmethod ig/pre-init-spec ::* [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::encoding
                   ::fallback-encoding
                   ::store
                   ::progress-fn]))


(s/def ::encoding string?)
(s/def ::fallback-encoding string?)
(s/def ::store fn?)
(s/def ::progress-fn fn?)
