<?php

use Illuminate\Database\Eloquent\Builder as EloquentBuilder;
use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    public function scopeWithoutBuilder(\Illuminate\Database\Eloquent\Builder $builder)
    {
    }

    public function scopeWrongBuilder(\Illuminate\Database\Eloquent\Builder $builder, $dummy)
    {
    }

    public function scopeCorrectBuilderButWithNull(\Illuminate\Database\Eloquent\Builder $builder)
    {
    }

    public function scopeWithoutTypeDeclaration(\Illuminate\Database\Eloquent\Builder $builder)
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
