package com.codeforcesvisualizer.util

const val BASE_URL = "https://codeforces.com"
const val CONTEST_LIST_URL = "http://codeforces.com/api/contest.list"
const val USER_STATUS_URL = "http://codeforces.com/api/user.status"
const val USER_EXTRA_URL = "http://codeforces.com/api/user.rating"

fun getProfileUrl(handle: String): String {
    return "http://codeforces.com/api/user.info?handles=$handle"
}