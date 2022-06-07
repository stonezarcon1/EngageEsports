# EngageEsports

# ENDPOINTS:

# /login

Permissions needed: Permit all

GET: No usage

POST: Supply a username and password as login details to authenticate a session.
Returns authenticated details and session info.

# /register/student

Permissions needed: Permit all

GET: No usage

POST: Supply a user object as a json. Example

{
"username":"test",
"password":"123",
"email":"test@test.com",
"firstName":"tester",
"lastName":"test",
"alias":"xxxTestxxx",
"classId":42
}

You must supply a value for all fields to avoid errors. All fields other
than "classId" accept a varchar[255]. classID accepts Long. Users of this
type will always be defaulted to "enabled" and have their "teacher" value
initialized as false.

The supplied classId must exist and be tied to a current teacher or this
endpoint will return an error

# /register/teacher

Permissions needed: Admin

GET: No usage

POST: Supply a user object as a json. Example

{
"username":"teacher",
"password":"123",
"email":"test@test.com",
"firstName":"tester",
"lastName":"test",
"alias":"xxxTeacherxxx",
"classId":42
}

You must supply a value for all fields to avoid errors. All fields other
than "classId" accept a varchar[255]. classID accepts Long. Users of this
type will always be defaulted to "enabled" and have their "teacher" value
initialized as true.

# /profile/player-profile

Permissions needed: User

GET: Requires an authenticated session. Returns player profile for current
session. 

POST: Requires an authenticated session. Applies @RequestBody to the 
"testField" field within the player profile.

# /profile/student-list

Permissions needed: Staff

GET: Requires an authenticated staff session. Returns all students that are
part of a class code.

POST: No usage