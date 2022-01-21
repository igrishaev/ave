(ns ave.db.hickary-cp
  (:require
   [hikari-cp.core :as cp]
   [integrant.core :as ig]))


(defmethod ig/init-key ::ig
  [_ params]
  (let [datasource
        (cp/make-datasource params)]
    {:datasource datasource}))


(defmethod ig/halt-key! ::ig
  [_ {:keys [datasource]}]
  (when datasource
    (cp/close-datasource datasource)))


(defmethod ig/pre-init-spec ::ig [_]
  ::cp/configuration-options)
