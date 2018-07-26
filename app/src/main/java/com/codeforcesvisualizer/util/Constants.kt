package com.codeforcesvisualizer.util

val BASE_URL = "https://codeforces.com"
val CONTEST_LIST_URL = "http://codeforces.com/api/contest.list"
val USER_STATUS_URL = "http://codeforces.com/api/user.status?handle="
val USER_EXTRA_URL = "http://codeforces.com/api/user.rating?handle="

fun getProfileUrl(handle: String, handle2: String): String {
    return "http://codeforces.com/api/user.info?handles=$handle;$handle2"
}