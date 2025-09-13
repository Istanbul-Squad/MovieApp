package com.karrar.movieapp.data.local


import javax.inject.Inject

interface AppConfiguration {

    fun getSessionId(): String?

    suspend fun saveSessionId(value: String)

    suspend fun saveRequestDate(key: String,value: Long)

    suspend fun getRequestDate(key: String): Long?

    suspend fun saveIsDarkTheme(isDarkTheme: Boolean)

    suspend fun getIsDarkTheme(): Boolean?

    suspend fun saveLanguage(language: String)

    fun getLanguage(): String?
}

class AppConfigurator @Inject constructor(private val dataStorePreferences: DataStorePreferences) :
    AppConfiguration {

    override fun getSessionId(): String? {
        return dataStorePreferences.readString(SESSION_ID_KEY)
    }

    override suspend fun saveSessionId(value: String) {
        dataStorePreferences.writeString(SESSION_ID_KEY, value)
    }

    override suspend fun saveRequestDate(key: String, value: Long) {
        dataStorePreferences.writeLong(key, value)
    }

    override suspend fun getRequestDate(key: String): Long? {
        return dataStorePreferences.readLong(key)
    }

    override suspend fun getIsDarkTheme(): Boolean? {
        return dataStorePreferences.readBoolean(DARK_THEME_KEY)
    }

    override suspend fun saveIsDarkTheme(isDarkTheme: Boolean) {
        dataStorePreferences.writeBoolean(DARK_THEME_KEY, isDarkTheme)
    }

    override fun getLanguage(): String? {
        return dataStorePreferences.readString(LANGUAGE_KEY)
    }

    override suspend fun saveLanguage(language: String) {
        dataStorePreferences.writeString(LANGUAGE_KEY, language)
    }

    companion object DataStorePreferencesKeys {
        const val SESSION_ID_KEY = "session_id"
        const val DARK_THEME_KEY = "dark_theme"
        const val LANGUAGE_KEY = "language"
    }
}