(ns ave.system.main
  (:require
   [clojure.java.io :as io]

   [aero.core :as aero]
   [integrant.core :as ig]
   [signal.handler :as signal]

   [clojure.tools.logging :as log]))


(defonce ^:private ^:dynamic
  *system* nil)


(defn ex-handler-default [e]
  (log/errorf e "Error while preparing and running the system")
  (System/exit 1))


(defn main

  [{:keys [config-map
           config-file
           config-resource

           ex-handler]}]

  (try

    (binding [*system* nil]

      (log/infof "Preparing the system...")

      (let [config
            (or config-map
                (-> config-file io/file aero/read-config
                    config-resource io/resource (or :todo) aero/read-config))

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
          (log/info "The system has been restarted"))))

    (catch Throwable e
      (let [handler
            (or ex-handler ex-handler-default)]
        (handler e)))))
