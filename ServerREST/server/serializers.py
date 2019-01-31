from rest_framework import serializers

from server.models import ResponseServer

from server.models import ImageSearch


class ResponseServerSerializer(serializers.ModelSerializer):
    class Meta:
        model = ResponseServer
        fields = ('result', 'score')


class ImageSearchSerializer(serializers.ModelSerializer):
    class Meta:
        model = ImageSearch
        fields = ('date', 'client', 'response')