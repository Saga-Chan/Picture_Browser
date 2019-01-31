# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render

# Create your views here.
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from server.models import ImageSearch

from server.serializers import ImageSearchSerializer


class ServerList(APIView):

    def get(self, request, format=None):
        serv = ImageSearch.objects.all()
        serializer = ImageSearchSerializer(serv)
        return Response(serializer.data)

    def post(self, request, format=None):
        errors = {"error": "body must not be empty"}
        if len(request.data) is not 0:
            """serializer = ImageSearchSerializer(data=request.data)
            if serializer.is_valid():
                serializer.save()
                response = Response(serializer.data, status=status.HTTP_201_CREATED)
                return response
            errors = serializer.errors
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)"""
            image = request.data
            #print(image)
        return Response('Récupération image OK', status=status.HTTP_201_CREATED)

class ServerListDetail(APIView):

    def get(self, request, pk, format=None):
        serv = self.get_object(pk)
        serializer = ImageSearchSerializer(serv)
        return Response(serializer.data)
