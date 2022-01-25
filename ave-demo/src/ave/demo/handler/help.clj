(ns ave.demo.handler.help
  (:require
   clojure.pprint

   [clojure.tools.logging :as log]
   [integrant.core :as ig]))


(defmethod ig/init-key ::*
  [_ _]
  (fn [ctx]
    (log/info "Help handler")
    {:status 200
     :headers {"content-type" "text/plain"}
     :body (with-out-str
             (clojure.pprint/pprint ctx))}))
