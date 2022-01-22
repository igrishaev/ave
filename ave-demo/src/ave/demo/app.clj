(ns ave.demo.app
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [integrant.core :as ig]
   #_ [ave.system.main :as main]
   )
  )



(defn get-config []
  (-> "config.edn"
      io/resource
      slurp
      ig/read-string))


#_
(def -c (get-config))

#_
(ig/load-namespaces -c)


#_
(def sys (ig/init -c))

#_
(ig/halt! sys)



;; list of symbols

#_
(ave.demo.handler.api
 ave.demo.handler.hello
 ave.demo.handler.help
 ave.demo.handler.not-found
 ave.interceptor.json-body
 ave.interceptor.json-response
 ave.interceptor.keyword-params
 ave.interceptor.params
 ave.interceptor.request-id
 ave.router-reitit
 ave.server-jetty)






#_
(defn main [& args]
  (main/main {}))
