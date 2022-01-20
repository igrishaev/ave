(ns ave.db.sqlite
  (:require
   [ave.db.jdbc :as jdbc]
   #_
   [clojure.java.jdbc :as jdbc]
   #_
   [clojure.java.jdbc.spec :as spec]
   [integrant.core :as ig]

   #_
   [clojure.spec.alpha :as s]))


(derive ::ig ::jdbc/ig)


(def defaults
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"})


#_
(defmethod ig/init-key ::ig
  [_ jdbc-spec]
  (ig/init-key :ave.db.jdbc/ig jdbc-spec))


#_
(defmethod ig/halt-key! ::ig
  [_ this]
  (ig/halt-key! :ave.db.jdbc/ig this))


(defmethod ig/prep-key :ig [_ jdbc-spec]
  (merge jdbc-spec defaults))


#_
(defmethod ig/pre-init-spec ::ig [_]
  ::spec/db-spec)
