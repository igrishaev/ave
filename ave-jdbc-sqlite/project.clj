(defproject com.github.igrishaev/ave-db-sqlite "0.1.0-SNAPSHOT"

  :description
  "JDBC/Sqlite wrapper for Ave"

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
  [[com.github.igrishaev/ave-db-jdbc]
   [org.xerial/sqlite-jdbc]])
