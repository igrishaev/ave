(ns ave.db.pg
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
  {:dbtype "postgresql"})


(defmethod ig/prep-key :ig [_ jdbc-spec]
  (merge jdbc-spec defaults))


#_
(defmethod ig/pre-init-spec ::ig [_]
  ::spec/db-spec)
