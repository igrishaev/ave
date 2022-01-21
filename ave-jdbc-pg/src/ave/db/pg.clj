(ns ave.db.pg
  (:require
   [ave.db.hickary-cp :as ave.cp]
   [integrant.core :as ig]))


(derive ::ig ::ave.cp/ig)


(def defaults
  {:adapter "postgresql"
   :port-number 5432})


(defmethod ig/prep-key :ig [_ pool-params]
  (merge defaults pool-params))
