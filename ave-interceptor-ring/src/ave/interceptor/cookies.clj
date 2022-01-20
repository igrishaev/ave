(ns ave.interceptor.cookies
  (:require
   [ring.middleware.cookies :as cookies]
   [integrant.core :as ig]

   [clojure.spec.alpha :as s]))


(defn make [& options]
  {:name ::interceptor
   :enter
   (fn [ctx]
     (update ctx :request cookies/cookies-request options))
   :leave
   (fn [ctx]
     (update ctx :response cookies/cookies-response options))})


(def default
  (make))


(defmethod ig/init-key ::ig
  [_ options]
  (make options))


(defmethod ig/pre-init-spec ::ig [_]
  ::config)


(s/def ::decoder fn?)
(s/def ::encoder fn?)


(s/def ::config
  (s/keys :opt-un [::decoder
                   ::encoder]))
