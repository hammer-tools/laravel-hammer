package net.rentalhost.plugins.laravel.hammer.services

object IlluminateService {
    object Database {
        object Eloquent {
            object Concerns {
                object HasGlobalScopes {
                    const val name: String = "\\Illuminate\\Database\\Eloquent\\Concerns\\HasGlobalScopes"
                }
            }

            object Builder {
                const val name: String = "\\Illuminate\\Database\\Eloquent\\Builder"
            }
        }
    }
}
