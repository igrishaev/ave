(ns ave.db.pg
  (:require
   [ave.db.hickary-cp :as ave.hickary]
   [integrant.core :as ig]))


(derive ::* ::ave.hickary/*)


(def defaults
  {:adapter "postgresql"
   :port-number 5432})


(defmethod ig/prep-key ::* [_ pool-params]
  (merge defaults pool-params))
