(ns lanterna-test.core
  (:gen-class)
  (:import [com.googlecode.lanterna.gui2 MultiWindowTextGUI WindowBasedTextGUI BasicWindow
            Window$Hint Panel LinearLayout Direction Borders]
           [com.googlecode.lanterna.screen TerminalScreen]
           [com.googlecode.lanterna.terminal DefaultTerminalFactory]
           [com.googlecode.lanterna.terminal.ansi UnixTerminal]))

(defn- run []
  (let [factory (DefaultTerminalFactory.)
        term    (.createTerminal factory)
        screen  (TerminalScreen. term)
        gui     (MultiWindowTextGUI. screen)
        window  (let [this (proxy [BasicWindow] ["mywindow"]
                             )
                      panel-root (doto (Panel.)
                                   (.setLayoutManager (LinearLayout. Direction/HORIZONTAL)))
                      panel-left (Panel.)
                      panel-middle (Panel.)
                      panel-right (Panel.)]
                  (.addComponent panel-root panel-left)
                  (.addComponent panel-root (doto (Borders/singleLineBevel "panel-middle")
                                              (.setComponent panel-middle)))
                  (.addComponent panel-root (doto (Borders/doubleLineBevel "panel-right")
                                              (.setComponent panel-right)))
                  (.setCloseWindowWithEscape this true)
                  (.setComponent this panel-root)
                  this)]
    (.setHints window [#_Window$Hint/EXPANDED
                       #_Window$Hint/NO_DECORATIONS
                       #_Window$Hint/NO_POST_RENDERING
                       Window$Hint/FULL_SCREEN])
    (.startScreen screen)
    (.addWindow gui window)
    #_(Thread/sleep 5000)
    (.waitUntilClosed window)
    (.stopScreen screen)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (run)
  (println "done"))
