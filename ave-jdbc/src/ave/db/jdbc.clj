(ns ave.db.jdbc
  (:require
   [clojure.java.jdbc :as jdbc]
   [clojure.java.jdbc.spec :as spec]
   [integrant.core :as ig]

   [clojure.spec.alpha :as s])

  (:import java.io.Closable))


(defmethod ig/init-key ::ig
  [_ jdbc-spec]
  (let [conn
        (jdbc/get-connection jdbc-spec)]
    (assoc jdbc-spec :connection conn)))


(defmethod ig/halt-key! ::ig
  [_ {:keys [connection]}]
  (when connection
    (.close ^Closable connection)))


(defmethod ig/pre-init-spec ::ig [_]
  ::spec/db-spec)
