(ns ave.system.main
  (:require
   ave.system.readers

   [aero.core :as aero]
   [integrant.core :as ig]
   [signal.handler :as signal]

   [clojure.pprint :as pprint]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.tools.logging :as log]))


(defonce ^:private
  -system (atom nil))


(defn- -set-system [config]
  (reset! -system (ig/init config)))


(defn- -get-system []
  @-system)


(defn ex-handler-default
  [e & [options]]
  (log/errorf e "Error while running the system, params: %s"
              (with-out-str
                (pprint/pprint options)))
  (System/exit 1))


(defn -to-ordered-map [config]

  (if-let [{:ave/keys [order]}
           config]

    (let [fn-cmp
          (fn [key1 key2]
            (compare
             (get order key1 Integer/MAX_VALUE)
             (get order key2 Integer/MAX_VALUE)))]

      (sorted-map-by fn-cmp (mapcat seq config)))

    config))


(defn -pre-load-config
  [{:as options
    :keys [config-file
           config-resource
           profile]}]

  (or (when config-file
        (-> config-file
            io/file
            io/reader
            (aero/read-config {:profile profile})))

      (when config-resource
        (-> config-resource
            io/resource
            (or (throw
                 (ex-info
                  (format "Resource %s doesn't exist" config-resource)
                  {:type ::load-config
                   :resource config-resource})))
            (aero/read-config {:profile profile})))

      (throw
       (ex-info
        "Neither config file nor resource is specified"
        {:type ::load-config
         :options options}))))


(defn load-config [options]
  (-> options
      -pre-load-config
      -to-ordered-map))


(defn add-signals [config]

  (signal/with-handler :term
    (log/infof "Caught SIGTERM, quitting")
    (ig/halt! (-get-system))
    (log/infof "The system has been stopped")
    (System/exit 0))

  (signal/with-handler :int
    (log/info "Caught SIGINT, quitting")
    (ig/halt! (-get-system))
    (log/infof "The system has been stopped")
    (System/exit 0))

  (signal/with-handler :hup
    (log/info "Caught SIGHUP, restarting")
    (ig/halt! (-get-system))
    (-set-system config)
    (log/info "The system has been restarted")))


(defn -load-namespaces [config]

  (let [ns-syms
        (ig/load-namespaces config)]

    (log/infof "Loaded namespaces: %s"
               (with-out-str
                 (println)
                 (doseq [sym ns-syms]
                   (println " -" sym))))))


(defn main

  [{:as options
    :keys [config
           ex-handler]}]

  (try

    (log/infof "Preparing the system...")

    (let [config
          (or config
              (load-config options))]

      (-load-namespaces config)

      (-set-system config)

      (log/infof "The system has been started")

      (add-signals config)

      nil)

    (catch Throwable e
      (let [handler
            (or ex-handler
                ex-handler-default)]
        (handler e options)))))
