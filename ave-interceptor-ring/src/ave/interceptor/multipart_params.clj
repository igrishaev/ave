(ns ave.interceptor.multipart-params
  (:require
   [ring.middleware.multipart-params :as mp]
   [integrant.core :as ig]

   [clojure.spec.alpha :as s]))


(defn make [& [options]]
  {:name ::interceptor
   :enter
   (fn [ctx]
     (update ctx :request mp/multipart-params-request options))})


(def default
  (make))


(defmethod ig/init-key ::ig
  [_ options]
  (make options))


(defmethod ig/pre-init-spec ::ig [_]
  ::config)


(s/def ::config
  (s/keys :opt-un [::encoding
                   ::fallback-encoding
                   ::store
                   ::progress-fn]))

(s/def ::encoding string?)
(s/def ::fallback-encoding string?)
(s/def ::store fn?)
(s/def ::progress-fn fn?)
