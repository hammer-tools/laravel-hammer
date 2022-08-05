<?php

use Illuminate\Database\Eloquent\Builder as EloquentBuilder;
use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    public function <weak_warning descr="🔨 Laravel Hammer: missing first parameter.">scopeWithoutBuilder</weak_warning>()
    {
    }

    public function scopeWrongBuilder(<weak_warning descr="🔨 Laravel Hammer: wrong first parameter type.">\Closure</weak_warning> $builder, $dummy)
    {
    }

    public function scopeCorrectBuilderButWithNull(<weak_warning descr="🔨 Laravel Hammer: wrong first parameter type.">EloquentBuilder|null</weak_warning> $builder)
    {
    }

    public function scopeWithoutTypeDeclaration(<weak_warning descr="🔨 Laravel Hammer: missing first parameter type.">$builder</weak_warning>)
    {
    }

    // Not applicable:

    public function scopeCorrectBuilder(EloquentBuilder $builder)
    {
        $dummy = new class {
            public function scopeNotReal() {
            }
        };
    }
}
