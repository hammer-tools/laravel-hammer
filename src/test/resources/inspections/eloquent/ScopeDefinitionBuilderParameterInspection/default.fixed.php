<?php

use Illuminate\Database\Eloquent\Builder as EloquentBuilder;
use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    public function scopeWithoutBuilder(EloquentBuilder $builder)
    {
    }

    public function scopeWrongBuilder(EloquentBuilder $builder, $dummy)
    {
    }

    public function scopeWithoutTypeDeclaration(EloquentBuilder $builder)
    {
    }

    // Not applicable:

    public function scopeCorrectBuilder(EloquentBuilder $builder)
    {
    }
}
