(defproject ave/db-pg "0.1.0-SNAPSHOT"

  :description
  "JDBC/Postgresql wrapper for Ave"

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
  [[ave/db-jdbc]
   [org.postgresql/postgresql]])
