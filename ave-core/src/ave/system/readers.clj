(ns ave.system.readers
  (:require
   [aero.core :as aero]
   [integrant.core :as ig]))


(defmethod aero/reader 'ig/ref
  [_ _ the-key]
  (ig/ref the-key))


(defmethod aero/reader 'ig/refset
  [_ _ the-key]
  (ig/refset the-key))
