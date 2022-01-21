(ns ave.system.main
  (:require
   [clojure.java.io :as io]

   [aero.core :as aero]
   [integrant.core :as ig]
   [signal.handler :as signal]

   [clojure.tools.logging :as log]))


(defonce ^:private ^:dynamic
  *system* nil)


(defn main

  [{:keys [config-map
           config-path
           config-resource]}]

  (binding [*system* nil]

    (log/infof "Preparing the system...")

    (let [config
          (or config-map
              (-> config-path io/file aero/read-config
                  config-resource io/resource (or ...) aero/read-config))

          ns-seq
          (ig/load-namespaces config)]

      (log/infof "Loaded namespaces: %s" ns-seq)

      (set! *system* (ig/init config))

      (log/infof "The system has been started")

      (signal/with-handler :term
        (log/infof "Caught SIGTERM, quitting")
        (ig/halt! *system*)
        (log/infof "The system has been stopped")
        (System/exit 0))

      (signal/with-handler :int
        (log/info "Caught SIGINT, quitting")
        (ig/halt! *system*)
        (log/infof "The system has been stopped")
        (System/exit 0))

      (signal/with-handler :hup
        (log/info "Caught SIGHUP, restarting")
        (ig/halt! *system*)
        (set! *system* (ig/init config))
        (info "The system has been restarted")))))
