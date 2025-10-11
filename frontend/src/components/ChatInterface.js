import React, { useState } from 'react';
import { Send, Bot, User, FileText } from 'lucide-react';
import { chatWithDocuments } from '../services/api';

const ChatInterface = () => {
  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!inputValue.trim() || isLoading) return;

    const userMessage = { type: 'user', content: inputValue.trim() };
    setMessages(prev => [...prev, userMessage]);
    setInputValue('');
    setIsLoading(true);

    try {
      const response = await chatWithDocuments(inputValue.trim());
      const botMessage = {
        type: 'bot',
        content: response.answer,
        sources: response.sources || [],
        citations: response.citations || []
      };
      setMessages(prev => [...prev, botMessage]);
    } catch (error) {
      const errorMessage = {
        type: 'bot',
        content: 'Sorry, I encountered an error while processing your question. Please try again.',
        sources: [],
        citations: []
      };
      setMessages(prev => [...prev, errorMessage]);
    } finally {
      setIsLoading(false);
    }
  };

  const MessageBubble = ({ message }) => (
    <div className={`flex ${message.type === 'user' ? 'justify-end' : 'justify-start'} mb-4`}>
      <div className={`flex max-w-xs lg:max-w-md ${message.type === 'user' ? 'flex-row-reverse' : 'flex-row'}`}>
        <div className={`flex-shrink-0 ${message.type === 'user' ? 'ml-3' : 'mr-3'}`}>
          <div className={`h-8 w-8 rounded-full flex items-center justify-center ${
            message.type === 'user' ? 'bg-primary-500' : 'bg-gray-500'
          }`}>
            {message.type === 'user' ? (
              <User className="h-4 w-4 text-white" />
            ) : (
              <Bot className="h-4 w-4 text-white" />
            )}
          </div>
        </div>
        <div className={`px-4 py-2 rounded-lg ${
          message.type === 'user' 
            ? 'bg-primary-500 text-white' 
            : 'bg-gray-100 text-gray-900'
        }`}>
          <p className="text-sm">{message.content}</p>
          
          {message.type === 'bot' && message.sources && message.sources.length > 0 && (
            <div className="mt-2 pt-2 border-t border-gray-200">
              <div className="flex items-center text-xs text-gray-600 mb-1">
                <FileText className="h-3 w-3 mr-1" />
                Sources:
              </div>
              <div className="space-y-1">
                {message.sources.slice(0, 3).map((source, index) => (
                  <div key={index} className="text-xs text-gray-500 bg-gray-50 p-2 rounded">
                    {source.length > 100 ? `${source.substring(0, 100)}...` : source}
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );

  return (
    <div className="flex flex-col h-full">
      {/* Messages */}
      <div className="flex-1 overflow-y-auto p-4 space-y-4">
        {messages.length === 0 ? (
          <div className="text-center text-gray-500 mt-8">
            <Bot className="h-12 w-12 mx-auto mb-4 text-gray-300" />
            <p>Start a conversation by asking a question about your uploaded documents!</p>
          </div>
        ) : (
          messages.map((message, index) => (
            <MessageBubble key={index} message={message} />
          ))
        )}
        
        {isLoading && (
          <div className="flex justify-start mb-4">
            <div className="flex max-w-xs lg:max-w-md">
              <div className="flex-shrink-0 mr-3">
                <div className="h-8 w-8 rounded-full bg-gray-500 flex items-center justify-center">
                  <Bot className="h-4 w-4 text-white" />
                </div>
              </div>
              <div className="px-4 py-2 rounded-lg bg-gray-100">
                <div className="flex space-x-1">
                  <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce"></div>
                  <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{ animationDelay: '0.1s' }}></div>
                  <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{ animationDelay: '0.2s' }}></div>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>

      {/* Input */}
      <div className="border-t bg-white p-4">
        <form onSubmit={handleSendMessage} className="flex space-x-2">
          <input
            type="text"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            placeholder="Ask a question about your documents..."
            className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
            disabled={isLoading}
          />
          <button
            type="submit"
            disabled={!inputValue.trim() || isLoading}
            className="px-4 py-2 bg-primary-500 text-white rounded-lg hover:bg-primary-600 disabled:opacity-50 disabled:cursor-not-allowed flex items-center"
          >
            <Send className="h-4 w-4" />
          </button>
        </form>
      </div>
    </div>
  );
};

export default ChatInterface;

