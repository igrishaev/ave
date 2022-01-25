(ns ave.interceptor.session
  (:require
   [integrant.core :as ig]
   [ring.middleware.session :as session]

   [clojure.spec.alpha :as s]))


(defmethod ig/init-key ::ig
  [_ options]

  (let [options (session/session-options options)]

    {:name ::interceptor
     :enter
     (fn [ctx]
       (update ctx :request session/session-request options))
     :leave
     (fn [ctx]
       (update ctx :response session/session-response options))}))


(defmethod ig/pre-init-spec ::ig [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::store
                   ::root
                   ::cookie-name
                   ::cookie-attrs]))


(defn session-store? [x]
  (satisfies? ring.middleware.session.store/SessionStore x))


(s/def ::store session-store?)
(s/def ::root string?)
(s/def ::cookie-name string?)
(s/def ::cookie-attrs map?)
