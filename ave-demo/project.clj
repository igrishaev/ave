(def ave-version "0.1.0-SNAPSHOT")


(defproject com.github.igrishaev/ave-demo "0.1.0-SNAPSHOT"

  :description
  "Ave demo application"

  #_
  :source-paths
  #_
  ["src"
   "ava-hikari-cp/src"
   "ave-core/src"
   "ave-demo/src"
   "ave-interceptor-json/src"
   "ave-interceptor-ring/src"
   "ave-jdbc/src"
   "ave-jdbc-pg/src"
   "ave-jdbc-sqlite/src"
   "ave-logging-unilog/src"
   "ave-router-reitit/src"
   "ave-server-jetty/src"]

  :main ave.demo.app

  :profiles
  {:uberjar
   {:aot :all}}

  :dependencies
  [[org.clojure/clojure "1.10.3"]

   [com.github.igrishaev/ave-core ~ave-version]
   [com.github.igrishaev/ave-server-jetty ~ave-version]
   [com.github.igrishaev/ave-router-reitit ~ave-version]
   [com.github.igrishaev/ave-interceptor-json ~ave-version]
   [com.github.igrishaev/ave-interceptor-ring ~ave-version]
   [com.github.igrishaev/ave-db-pg ~ave-version]
   [com.github.igrishaev/ave-logging-unilog ~ave-version]])
