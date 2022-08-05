<?php

use Illuminate\Database\Eloquent\Builder as EloquentBuilder;
use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    public function <weak_warning descr="🔨 Laravel Hammer: missing first parameter (Builder).">scopeWithoutBuilder</weak_warning>()
    {
    }

    public function scopeWrongBuilder(\Closure $builder, $dummy)
    {
    }

    public function scopeCorrectBuilderButWithNull(EloquentBuilder|null $builder)
    {
    }

    public function scopeWithoutTypeDeclaration($builder)
    {
    }

    // Not applicable:

    public function scopeCorrectBuilder(EloquentBuilder $builder)
    {
    }
}
