import React, { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';
import { Upload, FileText, AlertCircle, CheckCircle } from 'lucide-react';
import { uploadDocument } from '../services/api';

const FileUpload = ({ onUploadSuccess, onUploadError }) => {
  const [isUploading, setIsUploading] = useState(false);
  const [uploadStatus, setUploadStatus] = useState(null);

  const onDrop = useCallback(async (acceptedFiles) => {
    const file = acceptedFiles[0];
    if (!file) return;

    setIsUploading(true);
    setUploadStatus(null);

    try {
      const response = await uploadDocument(file);
      setUploadStatus({ type: 'success', message: response.message, chunks: response.chunksCreated });
      onUploadSuccess?.(response);
    } catch (error) {
      const errorMessage = error.response?.data?.message || error.message || 'Upload failed';
      setUploadStatus({ type: 'error', message: errorMessage });
      onUploadError?.(error);
    } finally {
      setIsUploading(false);
    }
  }, [onUploadSuccess, onUploadError]);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: {
      'application/pdf': ['.pdf']
    },
    multiple: false,
    disabled: isUploading
  });

  return (
    <div className="w-full max-w-md mx-auto">
      <div
        {...getRootProps()}
        className={`
          border-2 border-dashed rounded-lg p-8 text-center cursor-pointer transition-colors
          ${isDragActive ? 'border-primary-500 bg-primary-50' : 'border-gray-300 hover:border-primary-400'}
          ${isUploading ? 'opacity-50 cursor-not-allowed' : ''}
        `}
      >
        <input {...getInputProps()} />
        
        <div className="flex flex-col items-center space-y-4">
          {isUploading ? (
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-500"></div>
          ) : (
            <Upload className="h-12 w-12 text-gray-400" />
          )}
          
          <div>
            <p className="text-lg font-medium text-gray-900">
              {isUploading ? 'Uploading...' : isDragActive ? 'Drop the PDF here' : 'Upload a PDF'}
            </p>
            <p className="text-sm text-gray-500 mt-1">
              Drag and drop a PDF file, or click to select
            </p>
          </div>
        </div>
      </div>

      {uploadStatus && (
        <div className={`mt-4 p-4 rounded-lg flex items-center space-x-3 ${
          uploadStatus.type === 'success' 
            ? 'bg-green-50 border border-green-200' 
            : 'bg-red-50 border border-red-200'
        }`}>
          {uploadStatus.type === 'success' ? (
            <CheckCircle className="h-5 w-5 text-green-500" />
          ) : (
            <AlertCircle className="h-5 w-5 text-red-500" />
          )}
          <div>
            <p className={`font-medium ${
              uploadStatus.type === 'success' ? 'text-green-800' : 'text-red-800'
            }`}>
              {uploadStatus.message}
            </p>
            {uploadStatus.chunks && (
              <p className="text-sm text-green-600">
                Created {uploadStatus.chunks} text chunks
              </p>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default FileUpload;

