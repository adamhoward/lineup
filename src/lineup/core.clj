(ns lineup.core
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]
        [clojure.tools.macro :as macro]))

(defn lineupfd [roster]
  (run-nc 1 [q]
          ;; initialize all of the positions
          (fresh [q1a q1b q1c q1d q1e
                  q2a q2b q2c q2d q2e
                  q3a q3b q3c q3d q3e
                  q4a q4b q4c q4d q4e]
                 ;; 4 quarters of 5 players (goalie in position e)
                 (== q [[q1a q1b q1c q1d q1e]
                        [q2a q2b q2c q2d q2e]
                        [q3a q3b q3c q3d q3e]
                        [q4a q4b q4c q4d q4e]])
                 ;; players must come from roster
                 (infd q1a q1b q1c q1d q1e
                       q2a q2b q2c q2d q2e
                       q3a q3b q3c q3d q3e
                       q4a q4b q4c q4d q4e
                       (apply domain roster))
                 (let [q1 [q1a q1b q1c q1d q1e]
                       q2 [q2a q2b q2c q2d q2e]
                       q3 [q3a q3b q3c q3d q3e]
                       q4 [q4a q4b q4c q4d q4e]]
                   (all
                    ;; player can't fill more than one position per quarter
                    (everyg distinctfd [q1 q2 q3 q4])
                    ;; no one plays in consecutive quarters (excluding halftime)
                    (everyg #(distinctfd (conj q2 %)) q1)
                    (everyg #(distinctfd (conj q4 %)) q3)))
                 ;; no one plays goalie more than once
                 (distinctfd [q1e q2e q3e q4e])
                 ;; everyone plays more than one quarter (50% rule)
                 )))
(comment
  (lineupfd [1 2 3 4 5 6 7 8 9 10])
  ;;=> ([[1 2 3 4 5][6 7 8 9 10][1 2 3 4 6][5 7 8 10 9]])
  )

;;; Experiments

(comment
  (run* [q]
        (== q true)))

(defn lineupo [roster ps]
  (macro/symbol-macrolet [_ (lvar)]
                         (all
                          (== [_ _ _ _ _] ps)
                          (everyg #(infd % (domain 1 2 3 4 5 6 7 8 9)) ps))))

(comment
  (run 1 [q]
       (fresh [a1 a2 a3 a4 a5]
              (== q [a1 a2 a3 a4 a5])
              (infd a1 a2 a3 a4 a5 (domain 1 2 3 4 5))
              (distinctfd q))))

(defne not-membero [x l]
  ([_ []])
  ([_ [?y . ?r]]
     (!= x ?y)
     (not-membero x ?r)))

(comment (run 5 [q]
              (fresh [w x
                      y z]
                     (== q [[w x]
                            [y z]])
                     (infd w x y z (domain 1 2 3 4))
                                        ;(everyg distinctfd [[w x] [y z]])
                     ;;(everyg #(membero % [y z]) [w x])
                     ;;(everyg distinctfd [[w y z] [x y z]])
                     (everyg #(distinctfd (conj [y z] %)) [w x])
                     )))
