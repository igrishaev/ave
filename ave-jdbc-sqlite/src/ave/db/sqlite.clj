(ns ave.db.sqlite
  (:require
   [ave.db.jdbc :as ave.jdbc]
   [integrant.core :as ig]))


(derive ::ig ::ave.jdbc/ig)


(def defaults
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"})


(defmethod ig/prep-key :ig [_ jdbc-spec]
  (merge jdbc-spec defaults))
