#pragma once

#include "pch.h"
#include "MyMath.h"

double MyMath::sign(double a)
{
	return (a > 0) - (a < 0);
}
