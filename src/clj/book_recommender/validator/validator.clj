(ns book-recommender.validator.validator)

(defn validate-login-form
  "validates login form (username and password)"
  [username password]
  (if (< (count username) 3)
    "Username must have at least 3 characters!"
    (if (or (nil? (re-find #"\d+" password))(< (count password) 6))
      "Password must contain at least 6 characters, with at least one numeric character!"
      "")))

(defn validate-register-form
  "validates register form (username, password, first name, last name and repeated password)"
  [username password firstname lastname repeatedpwd]
  (if (< (count username) 3)
    "Username must have at least 3 characters!"
    (if (or (nil? (re-find #"\d+" password))(< (count password) 6))
      "Password must contain at least 6 characters, with at least one numeric character!"
      (if (not= password repeatedpwd)
        "Passwords don't match!"
        ""))))
