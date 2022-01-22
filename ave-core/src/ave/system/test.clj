(ns ave.system.test
  (:require
   [integrant.core :as ig]))


(defonce ^:private ^:dynamic
  *system* nil)


(defn fixture-system [config]
  (fn [t]
    (binding [*system* nil]
      (set! *system* (ig/init config))
      (t)
      (ig/halt! *system*))))


(defmacro bind-system
  {:style/indent 1}
  [[bind] & body]
  `(let [~bind *system*]
     ~@body))


(defmacro with-system
  {:style/indent 1}
  [[bind config] & body]
  `(let [system# (ig/init ~config)
         ~bind system#]
     (try
       ~@body
       (finally
         (ig/halt! system#)))))
