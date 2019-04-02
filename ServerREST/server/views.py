# -*- coding: utf-8 -*-
from __future__ import unicode_literals



# Create your views here.

from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from server.models import ImageSearch

from server.serializers import ImageSearchSerializer

from traitement.decode import buildAndReturnLabel

from traitement.retriever.indexing import indexing

from traitement.retriever.online_search import online_search


class ServerList(APIView):

    def get(self, request, format=None):
        serv = ImageSearch.objects.all()
        serializer = ImageSearchSerializer(serv)
        return Response(serializer.data)

    def post(self, request, format=None):
        errors = {"error": "body must not be empty"}
        if len(request.data) is not 0:

            """serializer = PostImageSerializer(data=request.data)
            if serializer.is_valid():
                serializer.save()"""
            """serializer = ImageSearchSerializer(data=request.data)            #print(image)
            if serializer.is_valid():
                serializer.save()
                response = Response(serializer.data, status=status.HTTP_201_CREATED)
                return response
            errors = serializer.errors
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)"""
            print(request.data["base64"])
            image_64 = request.data["base64"]
            i64 = image_64.split(',')[1]
            type = image_64.split(',')[0]
            if 'png' in type:
                img_type = 'png'
            else:
                img_type = 'jpg'
            image_path = buildAndReturnLabel(i64, img_type)

            indexing()
            online_search()

        return Response('Récupération image OK', status=status.HTTP_201_CREATED)

class ServerListDetail(APIView):

    def get(self, request, pk, format=None):
        serv = self.get_object(pk)
        serializer = ImageSearchSerializer(serv)
        return Response(serializer.data)
