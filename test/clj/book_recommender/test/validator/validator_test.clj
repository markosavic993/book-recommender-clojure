(ns book-recommender.test.validator.validator-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [book-recommender.validator.validator :refer :all]))

(deftest test-validate-login-form
  (testing "correct validation of login form")
  (let [short-username "a"
        short-password "a"
        password-without-number "abcdef"
        ok-username "marko"
        ok-password "marko123"
        bad-username-message "Username must have at least 3 characters!"
        bad-password-message "Password must contain at least 6 characters, with at least one numeric character!"]
    (is (= bad-username-message (validate-login-form short-username short-password)))
    (is (= bad-password-message (validate-login-form ok-username short-password)))
    (is (= bad-password-message (validate-login-form ok-username password-without-number)))
    (is (= "" (validate-login-form ok-username ok-password)))))

(deftest test-validate-registration-form
  (testing "correct validation of registration form")
  (let [short-username "a"
        short-password "a"
        password-without-number "abcdef"
        not-matching-password "123marko"
        ok-username "marko"
        ok-password "marko123"
        ok-firstname "marko"
        ok-lastname "savic"
        bad-username-message "Username must have at least 3 characters!"
        bad-password-message "Password must contain at least 6 characters, with at least one numeric character!"
        not-matching-passwords-message "Passwords don't match!"]
    (is (= bad-username-message (validate-register-form short-username short-password ok-firstname ok-lastname short-password)))
    (is (= bad-password-message (validate-register-form ok-username short-password ok-firstname ok-lastname short-password)))
    (is (= bad-password-message (validate-register-form ok-username password-without-number ok-firstname ok-lastname short-password)))
    (is (= not-matching-passwords-message (validate-register-form ok-username ok-password ok-firstname ok-lastname not-matching-passwords-message)))
    (is (= "" (validate-register-form ok-username ok-password ok-firstname ok-lastname ok-password)))))
