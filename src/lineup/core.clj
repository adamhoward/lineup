(ns lineup.core
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]
        [clojure.tools.macro :as macro]))

(comment
  (run* [q]
        (== q true)))

(comment
  (run* [q]
        (appendo [] q [1 2 3 4 5 6 7 8 9 10])))

(comment (run 1 [q]
              (fresh [vars]
                     (== q vars)
                     (everyg #(infd % (domain 1 2 3 4 5 6 7 8 9)) (repeatedly 81 lvar)))))

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

(run 1 [q]
     (fresh [w x
             y z]
            (== q [[w x]
                   [y z]])
            (infd w x y z (domain 1 2 3 4))
            (everyg distinctfd [[w x] [y z]])
            (everyg #(membero % [y z]) [w x])))

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
                   ;; player can't fill more than one position per quarter
                   (everyg distinctfd [q1 q2 q3 q4]))
                 ;; no one plays goalie more than once
                 (distinctfd [q1e q2e q3e q4e])
                 ;; everyone plays more than one quarter (50% rule)
                 )))

(comment
  (lineupfd [1 2 3 4 5 6 7 8 9 10])
  ;;=> ([[1 2 3 4 5][1 2 3 4 5][1 2 3 4 5][1 2 3 4 5]])
  )

(comment
  (let [q1 [q1a q1b q1c q1d q1e]
                       q2 [q2a q2b q2c q2d q2e]
                       q3 [q3a q3b q3c q3d q3e]
                       q4 [q4a q4b q4c q4d q4e]]
                   ;; no one plays in consecutive quarters
                   ;;(everyg #(not-membero % q2) q1)
                   ;;(everyg distinctfd [[q1a q2a] [q1b q2b] [q1c q2c] [q1d q2e]])
                   ;;
                   ))