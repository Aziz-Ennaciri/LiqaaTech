import React from 'react';
import { Card, Tag, Button, Space, Typography } from 'antd';
import { CalendarOutlined, EnvironmentOutlined, UserOutlined, DollarOutlined } from '@ant-design/icons';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';

const { Title, Text } = Typography;

const EventCard = ({ event }) => {
    const navigate = useNavigate();

    const getStatusColor = (status) => {
        switch (status.toLowerCase()) {
            case 'upcoming':
                return 'blue';
            case 'ongoing':
                return 'green';
            case 'past':
                return 'gray';
            case 'cancelled':
                return 'red';
            default:
                return 'default';
        }
    };

    const handleViewDetails = () => {
        navigate(`/events/${event.id}`);
    };

    return (
        <Card
            hoverable
            cover={
                event.imageUrl ? (
                    <img
                        alt={event.title}
                        src={event.imageUrl}
                        style={{ height: 200, objectFit: 'cover' }}
                    />
                ) : (
                    <div
                        style={{
                            height: 200,
                            background: '#f0f0f0',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center'
                        }}
                    >
                        <Text type="secondary">No Image Available</Text>
                    </div>
                )
            }
            actions={[
                <Button type="primary" onClick={handleViewDetails}>
                    View Details
                </Button>
            ]}
        >
            <Space direction="vertical" size="small" style={{ width: '100%' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <Tag color={getStatusColor(event.status)}>{event.status}</Tag>
                    <Tag color="purple">{event.categoryName}</Tag>
                </div>

                <Title level={4} style={{ margin: 0 }}>
                    {event.title}
                </Title>

                <Text type="secondary" ellipsis={{ rows: 2 }}>
                    {event.description}
                </Text>

                <Space direction="vertical" size="small" style={{ width: '100%' }}>
                    <Space>
                        <CalendarOutlined />
                        <Text>
                            {format(new Date(event.startDateTime), 'MMM dd, yyyy HH:mm')}
                        </Text>
                    </Space>

                    <Space>
                        <EnvironmentOutlined />
                        <Text>{event.location}</Text>
                    </Space>

                    <Space>
                        <UserOutlined />
                        <Text>
                            {event.availableSpots} spots available
                        </Text>
                    </Space>

                    <Space>
                        <DollarOutlined />
                        <Text strong>${event.ticketPrice}</Text>
                    </Space>
                </Space>
            </Space>
        </Card>
    );
};

export default EventCard; 