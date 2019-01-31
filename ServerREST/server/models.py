# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models


# Create your models here.

class ResponseServer(models.Model):
    result = models.URLField()
    score = models.DecimalField(decimal_places=2, max_digits=10)


class ImageSearch(models.Model):
    date = models.DateTimeField(auto_now_add=True)
    client = models.CharField(max_length=100)
    response = models.OneToOneField(ResponseServer, on_delete=models.CASCADE, primary_key=True)
