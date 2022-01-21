(defproject ave/logging-unilog "0.1.0-SNAPSHOT"

  :description
  "JDBC wrapper for Ave"

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
    []}}

  :dependencies
  [[spootnik/unilog]
   [ch.qos.logback/logback-classic]])
