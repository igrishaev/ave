(ns ave.interceptor.cookies
  (:require
   [integrant.core :as ig]
   [ring.middleware.cookies :as cookies]

   [clojure.spec.alpha :as s]))


(defmethod ig/init-key ::*
  [_ options]
  {:name ::interceptor
   :enter
   (fn [ctx]
     (update ctx :request cookies/cookies-request options))
   :leave
   (fn [ctx]
     (update ctx :response cookies/cookies-response options))})


(defmethod ig/pre-init-spec ::* [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::decoder
                   ::encoder]))

(s/def ::decoder fn?)
(s/def ::encoder fn?)
