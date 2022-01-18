(defproject ave/server-jetty "0.1.0-SNAPSHOT"

  :description
  "HTTP Jetty server for Ave"

  :plugins [[lein-parent "0.3.8"]]

  :parent-project
  {:path "../project.clj"
   :inherit [:deploy-repositories
             :license
             :managed-dependencies
             :plugins
             :repositories
             :scm
             :test-selectors
             :url
             [:profiles :dev]]}

  :profiles
  {:dev
   {:resource-paths ["../dev-resources"]
    :dependencies
    [[ch.qos.logback/logback-classic]]}}

  :dependencies
  [[ave/core]
   [org.clojure/tools.logging]
   [integrant]
   [exoscale/interceptor]
   [ring/ring-jetty-adapter]])
