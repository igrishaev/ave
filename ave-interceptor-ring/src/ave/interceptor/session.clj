(ns ave.interceptor.session
  (:require
   [ring.middleware.session :as session]
   [integrant.core :as ig]

   [clojure.spec.alpha :as s]))


(defn make [& [options]]

  (let [options (session/session-options options)]

    {:name ::interceptor
     :enter
     (fn [ctx]
       (update ctx :request session/session-request options))
     :leave
     (fn [ctx]
       (update ctx :response session/session-response options))}))


(def default
  (make))


(defmethod ig/init-key ::ig
  [_ options]
  (make options))


(defmethod ig/pre-init-spec ::ig [_]
  ::config)


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
