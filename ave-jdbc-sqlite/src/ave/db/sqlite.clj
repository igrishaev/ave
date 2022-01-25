(ns ave.db.sqlite
  (:require
   [ave.db.jdbc :as ave.jdbc]
   [integrant.core :as ig]))


(derive ::* ::ave.jdbc/*)


(def defaults
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"})


(defmethod ig/prep-key ::* [_ jdbc-spec]
  (merge jdbc-spec defaults))
