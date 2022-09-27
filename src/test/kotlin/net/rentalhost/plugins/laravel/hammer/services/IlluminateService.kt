package net.rentalhost.plugins.laravel.hammer.services

object IlluminateService {
    val eloquentFiles: List<String> = listOf(
        "Illuminate/Database/Eloquent/Model.php",
        "Illuminate/Database/Eloquent/Builder.php",
        "Illuminate/Database/Eloquent/Concerns/HasGlobalScopes.php",
    )
}
